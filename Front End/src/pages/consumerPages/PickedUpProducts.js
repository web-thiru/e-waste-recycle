import React, { useState, useEffect } from 'react';
import Helmet from '../../components/Helmet';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import { ProductsLogo } from '../productPages/Product';

import {
    Table,
    Container,
    Image,
    Col,
    Button,
    ButtonToolbar,
    ButtonGroup,
    Modal,
    Card
} from 'react-bootstrap';

export default function PickedUpProducts() {

    const [allProducts, setAllProducts] = useState([]);

    useEffect(() => {
        axios
            .get(`http://localhost:9090/consumer/products/pickedUpProducts/1`)
            .then((response) => {
                setAllProducts(response.data);
            })
            .catch((error) => {
                console.log(error);
            });

    }, []);

    return (
        <Helmet title="Consumer - Picked Up Products ">
            <div className="p-3">
                <Container className="text-center">
                    <h4><strong>Picked Up Products</strong></h4>
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
                            {allProducts != null ? allProducts.map((d) => (
                                <tr key={d.productId} className="p-2">
                                    <td className="d-flex text-start">
                                        <Image
                                            src={ProductsLogo.get(d.category)}
                                            width="60"
                                            height="60"
                                            className='my-auto'
                                        />
                                        <Col className="p-2">
                                            <h5>
                                                {d.brand} {d.name}
                                            </h5>
                                            <h6>{d.category}</h6>
                                            <h6>Last Modified: {d.modifiedDate}</h6>
                                        </Col>
                                    </td>
                                    <td className="text-center align-middle">
                                        <span class="badge rounded-pill bg-success">Picked Up</span>
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
    )
}