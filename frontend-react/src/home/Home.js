import './Home.css';
import { Link } from "react-router-dom";
// This is the startpage, it will show a welcome message 
// and the most common action, a button to generate a floor path
function Home() {
  return (
    <div id='bodyContent'>
      <h1 id='homeTitle'>Welcome to the DM-Rougelite Companion</h1>
      <h2 id='homeSubTitle'>The easy way to generate Rougelite encounters!</h2>

      <div className='padder' id='wrapper'>
      <div id='first'>
        <p>Click the button below to generate a floor path</p>
        <Link to="/dashboard" style={{ textDecoration: 'none' }}>
         <button className='startButton' >Generate floor path</button>
        </Link>
      </div>

      <div id='second'>
        <p>Click to list all the items</p>
        <Link to="/dashboard" style={{ textDecoration: 'none' }}>
          <button className='startButton' >List all Items</button>
        </Link>
        
      </div>
    </div>
      </div>
     

  );
}

export default Home;
