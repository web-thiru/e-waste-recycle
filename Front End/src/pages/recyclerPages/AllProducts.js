import React, { useState, useEffect } from 'react';
import {
  Table,
  Container,
  Image,
  Col,
  Button,
  ButtonToolbar,
  ButtonGroup
} from 'react-bootstrap';

import { ProductsLogo } from '../productPages/Product';
import addIcon from '../../images/addIcon.png'
import Helmet from '../../components/Helmet';
import axios from "axios";
import { useNavigate } from 'react-router-dom';



export default function AllProducts() {

  const [allProducts, setAllProducts] = useState([]);

  const navigate = useNavigate();


  useEffect(() => {
    try {
      axios.get(`http://localhost:9090/recycler/products/notPickedUpProducts`)
        .then((response) => {
          setAllProducts(response.data);
        })
    } catch (error) {
      console.log(error);
    };

  }, []);

  const handlePickUp = (data) => {
    console.log(data);

    const productDto = {
      "productId": data.productId,
      "name": data.name,
      "category": data.category,
      "brand": data.brand,
      "accessories": data.accessories,
      "manufacturedYear": data.manufacturedYear,
      "consumerId": data.consumerId,
      "recyclerId": localStorage.getItem('UserId'),
      "status": true
    }

    console.log(productDto);
    try {
      axios.put(`http://localhost:9090/products/pickUpProduct/${data.productId}`, productDto)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });


    } catch (error) {
      console.log(error);
    }
  }


  return (
    <Helmet title="Recycler - All Products ">
      <div className="p-3">
        <Container className="text-center">
          <h4><strong>All Products</strong></h4>
          <Table
            variant="dark"
            className="p-2 rounded rounded-3 overflow-hidden"
            bordered
          >
            <thead>
              <tr>
                <th>Product</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {allProducts != null ? allProducts.map((data) => (
                <tr key={data.productId} className="p-2">
                  <td className="d-flex text-start align-middle">
                    <Image
                      src={ProductsLogo.get(data.category)}
                      width="60"
                      height="60"
                      className='my-auto'
                    />
                    <Col className="p-2">
                      <h5>
                        {data.brand} {data.name}
                      </h5>
                      <h6>{data.category}</h6>
                    </Col>
                  </td>
                  <td className="text-center align-middle">
                    {
                      data.status ? <span class="badge rounded-pill bg-success">Picked Up</span> : <Button class="badge bg-danger" pill onClick={() => handlePickUp(data)}>Pick Up</Button>
                    }
                  </td>
                </tr>
              )) : <></>
              }
            </tbody>
          </Table>
          {
            allProducts.length === 0 ? <h4>No Products Available</h4> : <></>
          }
        </Container>
      </div>
    </Helmet>
  );
}

