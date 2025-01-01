import './Home.css';

// This is the startpage, it will show a welcome message 
// and the most common action, a button to generate a floor path
function Home() {
  return (
    <div>
      <h1>Welcome to the DM-Rougelite Companion</h1>
      <h2>The easy way to generate Rougelite encounters!</h2>

      <p>Click the button below to generate a floor path</p>
      <button>Generate floor path</button>
    </div>
  );
}

export default Home;
