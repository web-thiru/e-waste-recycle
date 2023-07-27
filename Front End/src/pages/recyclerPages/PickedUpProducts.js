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
import { Link, useNavigate } from 'react-router-dom';



export default function PickedUpProducts() {

  const [allProducts, setAllProducts] = useState([]);

  const navigate = useNavigate();


  useEffect(() => {
    axios
      .get(`http://localhost:9090/recycler/products/viewAllProducts`)
      .then((response) => {
        setAllProducts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });

  }, []);


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
                <th>Actions</th>
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
                      <h6>Last Modified: {data.modifiedDate}</h6>
                    </Col>
                  </td>
                  <td className="align-middle">
                    <ButtonToolbar className="justify-content-evenly">
                      <ButtonGroup className="me-2">
                        <Button variant="info" >info</Button>
                      </ButtonGroup>
                      <ButtonGroup className="me-2">
                        <Link to={`/consumer/editProduct/${data.productId}`} state={{ product: data }}>
                          <Button variant="info">Edit</Button>
                        </Link>
                      </ButtonGroup>
                      <ButtonGroup>
                        <Button variant="danger" onClick={() => handleDelete(data.productId)}>Delete</Button>
                      </ButtonGroup>
                    </ButtonToolbar>
                  </td>
                  <td className="text-center align-middle">
                    <span class="badge rounded-pill bg-danger">Not Picked Up</span>
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

