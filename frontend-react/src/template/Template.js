import './Template.css';
import { useState } from 'react';
import { Outlet, Link } from "react-router-dom";

function Template() {

  const [content, setContent] = useState('Welcome to the dynamic content page!');

  const loadContent = (newContent) => {
    setContent(newContent);
  };

  const creditStyle = {
    color: 'hotpink'
  }

  return (
    <>
      {/* Navbar */}
      <nav className="navbar">
        <Link to="/" style={{ textDecoration: 'none' }}>
          <h1 className="title">DM-Rougelite Companion</h1>
        </Link>
        <div className="nav-buttons">

          {/* Dropdown 1 */}
          <div className="dropdown">
            <Link to="/" style={{ textDecoration: 'none' }}>
              <button className="button">Generate floor path</button>
            </Link>
          </div>

          {/* Dropdown 2 */}
          <div className="dropdown">
            <button className="button">See items</button>
            <div className="dropdown-content">
              <a href="#" onClick={() => loadContent('Menu 2 - Item 1 Content')}>Items</a>
              <a href="#" onClick={() => loadContent('Menu 2 - Item 2 Content')}>Enemies</a>
              <a href="#" onClick={() => loadContent('Menu 3 - Item 3 Content')}>Rooms</a>
            </div>
          </div>

          <div className="dropdown">
            <Link to="/" style={{ textDecoration: 'none' }}>
              <button className="button">FAQ</button>
            </Link>
          </div>

          <div className="dropdown">
            <Link to="/" style={{ textDecoration: 'none' }}>
              <button className="button questionMark">?</button>
            </Link>
          </div>

        </div>
      </nav>

      {/* Main Content */}
      <main className="main-content">

        <Outlet />

      </main>
      <footer className="footer">
        <p>A project by Palmfeldt and Badde00</p>

        <a target="_blank" style={creditStyle} href="https://icons8.com/icon/8Iw0xC1EvrxU/spyro">Dragon</a> icon by <a style={creditStyle}
          target="_blank" href="https://icons8.com">Icons8</a>

      </footer>
    </>
  );
}

export default Template;
