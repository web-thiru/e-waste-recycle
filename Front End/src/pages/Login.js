import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import logo from '../images/Logo.svg';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import Helmet from '../components/Helmet';
import { useNavigate, useParams } from 'react-router-dom';
import toast, { Toaster } from 'react-hot-toast';

export default function Login() {

  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    const { email, password } = data;

    const requestBody = {
      username: email,
      password,
    };

    try {
      axios.post("http://localhost:9090/auth/authenticate", requestBody)
        .then(response => {
          console.log(response.data)

          localStorage.setItem('Token', response.data.token);
          localStorage.setItem('UserId', response.data.id);
          localStorage.setItem('Role', response.data.role);
          localStorage.setItem('UserEmail', response.data.email);
          localStorage.setItem('UserName', response.data.username);
          console.log(response.status)

          if (response.status === 200) {
            toast.success('Successfull!');
          }

          if (localStorage.getItem('Role') === "ROLE_RECYCLER") {
            navigate('/recycler');
          } else {
            navigate('/consumer');
          }

        });

    } catch (error) {
      toast.error('Failed!');
      console.error(error);
    }
  };

  return (
    <Helmet title="log in">

      <Toaster position="top-center" toastOptions={{
        style: {
          color: 'white',
          backgroundColor: 'black'
        },
      }} />

      <Container>
        <Row className="d-flex justify-content-center align-items-center pt-4">
          <Col md={8} lg={5} xs={11}>
            <div className="border border-3 border-success bg-success"></div>
            <Card className="shadow">
              <Card.Body>
                <div className="mb-3 md-4">
                  <img
                    alt="Logo"
                    src={logo}
                    width="70"
                    height="70"
                    className="mx-auto d-block p-2"
                  />
                  <h2 className="fw-bold mb-2 text-center text-uppercase">
                    E-Waste Recycling Portal
                  </h2>
                  <h5 className=" mb-4 text-center">Login Page</h5>
                  <div className="mb-3">
                    <Form onSubmit={handleSubmit(onSubmit)}>
                      <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label className="text-center">
                          Email address
                        </Form.Label>
                        <Form.Control
                          type="email"
                          placeholder="Enter email"
                          {...register('email', {
                            required: 'Email is required',
                            pattern: {
                              value: /^\S+@\S+$/i,
                              message: 'Invalid email address',
                            },
                          })}
                        />
                        {errors.email && (
                          <Form.Text className="text-danger">
                            {errors.email.message}
                          </Form.Text>
                        )}
                      </Form.Group>

                      <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                          type="password"
                          placeholder="Password"
                          {...register('password', {
                            required: 'Password is required',
                            minLength: {
                              value: 8,
                              message:
                                'Password must be at least 8 characters long',
                            },
                          })}
                        />
                        {errors.password && (
                          <Form.Text className="text-danger">
                            {errors.password.message}
                          </Form.Text>
                        )}
                      </Form.Group>

                      <div className="d-grid">
                        <Button variant="success" type="submit">
                          Login
                        </Button>
                      </div>
                    </Form>
                    <div className="mt-3">
                      <p className="mb-0  text-center">
                        Don't have an account?{' '}
                        <a href="{''}" className="text-success fw-bold">
                          Sign Up
                        </a>
                      </p>
                    </div>
                  </div>
                </div>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </Helmet>
  );
}
