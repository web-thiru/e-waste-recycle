package productMicroservice.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.productMicroservice.exception.NoContentFoundException;
import com.productMicroservice.exception.ProductNotFoundException;
import com.productMicroservice.model.Product;
import com.productMicroservice.repository.ProductRepository;
import com.productMicroservice.service.ProductService;

class ProductServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceTest.class);

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(createProduct(1L));
        products.add(createProduct(2L));

        when(productRepository.findAll()).thenReturn(products);

        try {
            List<Product> result = productService.getAllProducts();

            assertFalse(result.isEmpty());
            assertEquals(products.size(), result.size());
            assertEquals(products.get(0), result.get(0));
            assertEquals(products.get(1), result.get(1));

            verify(productRepository).findAll();
        } catch (NoContentFoundException e) {
            LOGGER.error("NoContentFoundException occurred: {}", e.getMessage());
            fail("NoContentFoundException should not be thrown");
        }
    }

    @Test
    void testGetAllProducts_NoContentFoundException() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NoContentFoundException.class, () -> {
            productService.getAllProducts();
        });

        verify(productRepository).findAll();
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product product = createProduct(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        try {
            Product result = productService.getProductById(productId);

            assertNotNull(result);
            assertEquals(product, result);

            verify(productRepository).findById(productId);
        } catch (ProductNotFoundException e) {
            LOGGER.error("ProductNotFoundException occurred: {}", e.getMessage());
            fail("ProductNotFoundException should not be thrown");
        }
    }

    @Test
    void testGetProductById_ProductNotFoundException() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });

        verify(productRepository).findById(productId);
    }

    @Test
    void testSaveProduct() {
        Product product = createProduct(1L);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals(product, savedProduct);

        verify(productRepository).save(product);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        assertDoesNotThrow(() -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testDeleteProduct_ProductNotFoundException() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository).existsById(productId);
        verify(productRepository, never()).deleteById(productId);
    }

    private Product createProduct(Long productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setName("Product " + productId);
        product.setCategory("Category " + productId);
        product.setBrand("Brand " + productId);
        product.setAccessories("Accessories " + productId);
        product.setManufacturedYear(2022);
        product.setConsumerId(1L);
        product.setRecyclerId(1L);
        return product;
    }
}
