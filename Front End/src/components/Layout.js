import React, { Fragment } from "react";
import NavBar from "./Navbar";

import Routers from "./Routers";

const Layout = ({isLoggedIn,isAdmin,userName}) => {
  return (
    <Fragment>
        <NavBar/>
      <div>
        <Routers />
      </div>    
    </Fragment>
  );
};

export default Layout;
