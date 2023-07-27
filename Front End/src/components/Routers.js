import React from 'react';
import { Route, Navigate, Routes } from 'react-router-dom';

import AddProduct from '../pages/consumerPages/AddProduct';
import AllProducts from '../pages/recyclerPages/AllProducts';
import RecyclerProfile from '../pages/recyclerPages/RecyclerProfile';
import Home from '../pages/Home';
import NotFoundPage from '../pages/NotFoundPage';
import ConsumerProfile from '../pages/consumerPages/ConsumerProfile';
import MyProducts from '../pages/consumerPages/MyProducts';
import PickedUpProducts from '../pages/consumerPages/PickedUpProducts';
import EditProduct from '../pages/consumerPages/EditProduct';
import ConsumerRegistration from '../pages/consumerPages/ConsumerRegistration';
import RecyclerRegistration from '../pages/recyclerPages/RecyclerRegistration';
import Login from '../pages/Login';


export default function Routers() {

  return (
    <Routes>

      <Route path="/" element={<Navigate to="home" />} />
      <Route path="/home" element={<Home />} />
      <Route path="/registrationRecycler" element={<RecyclerRegistration />} />
      <Route path="/registrationConsumer" element={<ConsumerRegistration />} />
      <Route path="/login" element={<Login />} />

      <Route path="*" element={<NotFoundPage />} />

      <Route path="/consumer">
        <Route index path="" element={<MyProducts />} />
        <Route path="pickedUpProducts" element={<PickedUpProducts />} />
        <Route path="addProduct" element={<AddProduct />} />
        <Route path="editProduct/:id" element={<EditProduct />} />
        <Route path="myProfile" element={<ConsumerProfile />} />
      </Route>
      
      <Route path="/recycler">
        <Route index path="" element={<AllProducts />} />
        <Route path="myProfile" element={<RecyclerProfile />} />
        <Route path="pickedUpProducts" element={<PickedUpProducts />} />
      </Route>

    </Routes>
  );
}
