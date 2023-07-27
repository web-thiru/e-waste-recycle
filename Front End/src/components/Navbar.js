import React from 'react';
import logo from '../images/Logo.svg';
import { Button, Container, Navbar, Nav, Image, Badge } from 'react-bootstrap';
import { Link, useLocation, useNavigate, NavLink } from 'react-router-dom';

export default function NavBar() {

  const navigate = useNavigate();


  const path = useLocation();
  let pathName = path.pathname;

  const renderLinks = () => {

    if (pathName.startsWith('/consumer')) {
      return (
        <>
          <Nav className="text-center p-0">

            <NavLink
              exact
              to="/consumer/myProfile"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
              <Nav.Link as="div" >My Profile</Nav.Link>
            </NavLink>

            <NavLink
              exact
              to="/consumer"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
                <Nav.Link as="div">My Products</Nav.Link>
            </NavLink>
            <NavLink
              exact
              to="/consumer/pickedUpProducts"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
            <Nav.Link as="div">Picked up by Recycler</Nav.Link>
            </NavLink>
            <NavLink
              exact
              to="/consumer/allRecyclers"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
            </NavLink>
          </Nav>
          <Nav className="px-2 align-middle  text-center">
            <Link to="/home">
              <Button variant="danger" size="sm">
                Log Out
              </Button>
            </Link>
          </Nav>
        </>
      );
    } else if (pathName.startsWith('/recycler')) {
      return (
        <>
          <Nav className="text-center p-0">

            <NavLink
              exact
              to="/recycler/myProfile"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
              <Nav.Link as="div" >My Profile</Nav.Link>
            </NavLink>

            <NavLink
              exact
              to="/recycler"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
              <Nav.Link as="div">All Products</Nav.Link>
            </NavLink>

            <NavLink
              exact
              to="/recycler/pickedUpProducts"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
              <Nav.Link as="div">Picked up products</Nav.Link>
            </NavLink>
          </Nav>

          <Nav className="px-2 align-middle text-center">
            <Link to="/home">
              <Button variant="danger" size="sm">
                Log Out
              </Button>
            </Link>
          </Nav>
        </>
      );
    } else if (pathName.startsWith('/login')) {
      return (
        <>
          <Nav className="text-center p-0">

            <NavLink
              exact
              to="/"
              style={{ textDecoration: 'none' }}
              activeClassName="active"
              className="nav-link"
            >
              <Nav.Link as="div" >Home</Nav.Link>
            </NavLink>
            </Nav>

          </>
          );
    } else {
      return (
          <>
            <Nav className="text-center">
            </Nav>
            <Nav className="text-center">
              <Link to="/login">
                <Button variant="success" size="sm">
                  Log In
                </Button>
              </Link>
            </Nav>
          </>
          );

    }
  };

          return (
          <div className="p-2">
            <Navbar
              bg="dark"
              expand="lg"
              variant="dark"
              className="rounded-2 px-3 p-2 sticky-top "
            >

              <Navbar.Brand onClick={() => navigate('/home')}>
                <img
                  alt="Logo"
                  src={logo}
                  width="40"
                  height="40"
                  className="d-inline-block mx-2"

                />
                E-Waste Recycling Portal
              </Navbar.Brand>

              <Navbar.Toggle aria-controls="basic-navbar-nav" />
              <Navbar.Collapse
                className="justify-content-between"
              >
                {renderLinks()}
              </Navbar.Collapse>
            </Navbar>
          </div>
          );
}