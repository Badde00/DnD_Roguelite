import './Template.css';
import { useState } from 'react';

function Template() {

  const [content, setContent] = useState('Welcome to the dynamic content page!');

  const loadContent = (newContent) => {
    setContent(newContent);
  };

  return (
    <>
      {/* Navbar */}
      <nav className="navbar">
        <h1 className="title">DM-Rougelite Companion</h1>
        <div className="nav-buttons">
          {/* Dropdown 1 */}
          <div className="dropdown">
            <button className="button">Menu 1</button>
          </div>

          {/* Dropdown 2 */}
          <div className="dropdown">
            <button className="button">Menu 2</button>
            <div className="dropdown-content">
              <a href="#" onClick={() => loadContent('Menu 2 - Item 1 Content')}>Item 1</a>
              <a href="#" onClick={() => loadContent('Menu 2 - Item 2 Content')}>Item 2</a>
            </div>
          </div>

          {/* Dropdown 3 */}
          <div className="dropdown">
            <button className="button">Menu 3</button>
            <div className="dropdown-content">
              <a href="#" onClick={() => loadContent('Menu 3 - Item 1 Content')}>Item 1</a>
              <a href="#" onClick={() => loadContent('Menu 3 - Item 2 Content')}>Item 2</a>
            </div>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="main-content">
        <p>{content}</p>
      </main>
      <footer className="footer">
        <p>Footer</p>
        <a target="_blank" href="https://icons8.com/icon/8Iw0xC1EvrxU/spyro">dragon</a> icos by <a target="_blank" href="https://icons8.com">Icons8</a>
      </footer>
    </>
  );
}

export default Template;
