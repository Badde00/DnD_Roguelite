import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css';
// This is the main template for all possible pages
import Template from './template/Template';
import Home from './pages/home/Home';
import ItemList from './pages/itemList/itemList';

/* import reportWebVitals from './reportWebVitals'; */

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<Template />}>
        <Route index element={<Home />} />
        <Route path="itemList" element={<ItemList />} />
        
      </Route>
    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
/* reportWebVitals(); */
