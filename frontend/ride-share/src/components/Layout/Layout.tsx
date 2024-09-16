import React from "react"
import AppMenu from "../AppMenu/AppMenu"
import { Outlet } from "react-router-dom"

const Layout: React.FC = () => {
    return <>
        <AppMenu />
        <Outlet />
    </>
}

export default Layout;