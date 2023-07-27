import React, { useState, useEffect } from 'react';
import {
  Container,
  CardGroup,
  Card,
  Row,
  Col,
  Form,
  Button,
} from 'react-bootstrap';

import { useForm } from 'react-hook-form';
import addProduct from '../../images/addProduct.png';
import Helmet from '../../components/Helmet';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import toast, { Toaster } from 'react-hot-toast';

export default function AddProduct() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
    getValues,
    setValue,
  } = useForm();

  // const data = {
  //   name: '',
  //   category: '',
  //   brand: '',
  //   accessories: '',
  //   manufacturedYear: null,
  // };

  const onSubmit = (data) => {
    const productDto = {
      "productId": 0,
      "name": data.name,
      "category": data.category,
      "brand": data.brand,
      "accessories": data.accessories,
      "manufacturedYear": data.manufacturedYear,
      "consumerId": 1,
      "status":false
      // "recyclerId": 1
    }
    try {
      axios.post("http://localhost:9090/consumer/products/addProduct", productDto)
        .then(response => {
          if (response.status === 201) {
            toast.success("Successfully Added!!");
          }
          console.log(response)
        });
    } catch (error) {
      toast.error("Failed to Add Product!!");
      console.log(error);
    }
  };

  return (
    <Helmet title="Consumer - Add Products">
      <Toaster
        toastOptions={{
          style: {
            color: 'white',
            backgroundColor:'black'
          },
        }}
      />
      <Container>
        <CardGroup className="p-2">
          <Card>
            <Card.Body>
              <Card.Img
                variant="top"
                src={addProduct} />
              <Card.Title className="text-center">
                Recycle E-Waste Products
              </Card.Title>
            </Card.Body>
          </Card>
          <Card className="rounded-2" variant="dark">
            <Card.Body>
              <Card.Title className="text-center">Add Product Details</Card.Title>
              <hr />
              <Card.Text>
                <Form onSubmit={handleSubmit(onSubmit)}>
                  <Form.Group className="mb-2">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                      placeholder="Enter name"
                      {...register('name', {
                        required: 'Name is required',
                      })}
                    />
                    {errors.name && (
                      <Form.Text className="text-danger">
                        {errors.name.message}
                      </Form.Text>
                    )}
                  </Form.Group>
                  <Form.Group className="mb-2">
                    <Form.Label>Category</Form.Label>
                    <Form.Control
                      placeholder="Enter category"
                      {...register('category', {
                        required: 'Category is required',
                      })}
                    />
                    {errors.category && (
                      <Form.Text className="text-danger">
                        {errors.category.message}
                      </Form.Text>
                    )}
                  </Form.Group>

                  <Form.Group className="mb-2">
                    <Form.Label>Brand</Form.Label>
                    <Form.Control
                      placeholder="Enter brand"
                      {...register('brand', {
                        required: 'Brand is required',
                      })}
                    />
                    {errors.brand && (
                      <Form.Text className="text-danger">
                        {errors.brand.message}
                      </Form.Text>
                    )}
                  </Form.Group>
                  <Form.Group className="mb-2">
                    <Form.Label>Accessories</Form.Label>
                    <Form.Control
                      placeholder="Enter accessories"
                      {...register('accessories', {
                        required: 'Accessories is required',
                      })}
                    />
                    {errors.accessories && (
                      <Form.Text className="text-danger">
                        {errors.accessories.message}
                      </Form.Text>
                    )}
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label>Manufactured Year</Form.Label>
                    <Form.Control
                      type="number"
                      placeholder="Enter manufactured year"
                      {...register('manufacturedYear', {
                        required: 'Manufactured year is required',
                        min: {
                          value: 1900,
                          message: 'Invalid manufactured year',
                        },
                      })}
                    />
                    {errors.manufacturedYear && (
                      <Form.Text className="text-danger">
                        {errors.manufacturedYear.message}
                      </Form.Text>
                    )}
                  </Form.Group>
                  <div className="d-grid">
                    <Button variant="primary" className="mb-2" size="sm" type="submit">
                      Save
                    </Button>

                    <Button variant="secondary" size="sm" onClick={() => navigate("/consumer")}>
                      Cancel
                    </Button>
                  </div>
                </Form>
              </Card.Text>
            </Card.Body>
          </Card>
        </CardGroup>
      </Container>
    </Helmet >
  );
}
