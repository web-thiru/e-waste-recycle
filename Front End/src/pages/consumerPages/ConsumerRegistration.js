import React from 'react';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import logo from '../../images/Logo.svg';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import Helmet from '../../components/Helmet';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import toast, { Toaster } from 'react-hot-toast';


export default function ConsumerRegistration() {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    getValues,
  } = useForm();

  const onSubmit = async () => {
    const formData = getValues();

    const formDataDto = {
      "consumerId": 0,
      "username": formData.Username,
      "email": formData.email,
      "phoneNumber": formData.phoneNumber,
      "password": formData.password,
      "address": {
        "addressId": 0,
        "street": formData.address,
        "city": formData.city,
        "state": formData.state,
        "country": formData.country,
        "zipCode": formData.zip
      }

    }

    console.log(formDataDto);

    try {
      axios.post("http://localhost:9090/auth/registerConsumer", formDataDto).then(response => {
        console.log(response.status);
        if (response.status === 200) {
          toast.success('Successfull!!');
        }
      });
    } catch (err) {
      toast.error('Failed!');
      console.log(err);
    }

    navigate('/login');
    
  };

  return (
    <Helmet title="Consumer - Registration Page">
      <Container>
      <Toaster position="top-center" toastOptions={{
        style: {
          color: 'white',
          backgroundColor: 'black'
        },
      }} />
        <Row className="d-flex justify-content-center align-items-center p-3">
          <Col md={8} lg={5} xs={11}>
            <div className="border border-3 border-success bg-success"></div>
            <Card className="shadow">
              <Card.Body>
                <div className="mb-3">
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
                  <p className=" mb-3 text-center">
                    Consumer Registration Page
                  </p>
                  <div className="mb-3">
                    <Form onSubmit={handleSubmit(onSubmit)}>
                      <Form.Group className="mb-3">
                        <Form.Label className="text-center">
                          Username
                        </Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="Enter username"
                          {...register('Username', {
                            required: 'Username is required',
                          })}
                        />
                        {errors.organizationName && (
                          <Form.Text className="text-danger">
                            {errors.organizationName.message}
                          </Form.Text>
                        )}
                      </Form.Group>

                      <Form.Group className="mb-3">
                        <Form.Label className="text-center">
                          Email address
                        </Form.Label>
                        <Form.Control
                          type="email"
                          placeholder="Enter email"
                          {...register('email', {
                            required: 'Email is required',
                            pattern: {
                              value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
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

                      <Form.Group className="mb-3">
                        <Form.Label className="text-center">
                          Phone number
                        </Form.Label>
                        <Form.Control
                          type="number"
                          placeholder="Enter Phone no"
                          {...register('phoneNumber', {
                            required: 'Phone no is required',
                            minLength: {
                              value: 8,
                              message:
                                'Phone no must be 10 characters long',
                            }
                          })}
                        />
                        {errors.email && (
                          <Form.Text className="text-danger">
                            {errors.email.message}
                          </Form.Text>
                        )}
                      </Form.Group>


                      <Form.Group className="mb-3">
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

                      <Form.Group className="mb-3">
                        <Form.Label>Confirm Password</Form.Label>
                        <Form.Control
                          type="password"
                          placeholder="Confirm Password"
                          {...register('confirmPassword', {
                            required: 'Confirm Password is required',
                            validate: (value) =>
                              value === getValues('password') ||
                              'Passwords do not match',
                          })}
                        />
                        {errors.confirmPassword && (
                          <Form.Text className="text-danger">
                            {errors.confirmPassword.message}
                          </Form.Text>
                        )}
                      </Form.Group>

                      <Form.Group className="mb-3">
                        <Form.Label>Address</Form.Label>
                        <Form.Control
                          placeholder="1234 Main St"
                          {...register('address', {
                            required: 'Address is required',
                          })}
                        />
                        {errors.address && (
                          <Form.Text className="text-danger">
                            {errors.address.message}
                          </Form.Text>
                        )}
                      </Form.Group>

                      <Row className="mb-3">
                        <Form.Group as={Col}>
                          <Form.Label>City</Form.Label>
                          <Form.Control
                            placeholder="City"
                            {...register('city', {
                              required: 'City is required',
                            })}
                          />
                          {errors.city && (
                            <Form.Text className="text-danger">
                              {errors.city.message}
                            </Form.Text>
                          )}
                        </Form.Group>

                        <Form.Group as={Col}>
                          <Form.Label>State</Form.Label>
                          <Form.Control
                            placeholder="State"
                            {...register('state', {
                              required: 'State is required',
                            })}
                          />
                          {errors.city && (
                            <Form.Text className="text-danger">
                              {errors.city.message}
                            </Form.Text>
                          )}
                        </Form.Group>



                        <Form.Group as={Col}>
                          <Form.Label>Zip</Form.Label>
                          <Form.Control
                            placeholder="Zip"
                            {...register('zip', {
                              required: 'Zip is required',
                              pattern: {
                                value: /^\d{6}$/,
                                message: 'Zip must be a 6-digit number',
                              },
                            })}
                          />
                          {errors.zip && (
                            <Form.Text className="text-danger">
                              {errors.zip.message}
                            </Form.Text>
                          )}
                        </Form.Group>
                      </Row>

                      <Form.Group className="mb-3">
                        <Form.Label>Country</Form.Label>
                        <Form.Control
                          placeholder="India"
                          {...register('country', {
                            required: 'Country is required',
                          })}
                        />
                        {errors.country && (
                          <Form.Text className="text-danger">
                            {errors.country.message}
                          </Form.Text>
                        )}
                      </Form.Group>

                      <div className="d-grid">
                        <Button variant="success" type="submit">
                          Register
                        </Button>
                      </div>
                    </Form>
                    <div className="mt-3">
                      <p className="mb-0  text-center">
                        Already have an account?
                        <Link to="/login" className="text-success fw-bold">
                          Log In
                        </Link>
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