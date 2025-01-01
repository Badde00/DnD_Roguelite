import './paginator.css';
// This is the startpage, it will show a welcome message 
// and the most common action, a button to generate a floor path
function Paginator({ items }) {

  const [currentPage, setCurrentPage] = useState(1);
  const [currentItems, setCurrentItems] = useState(items);

  const handleItemsPerPageChange = (event) => {
    // Handle items per page change
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
    // Update currentItems based on the new page
  };


  return (
    <div>
      <div>
        <label>
          Items per page:
          <select value={itemsPerPage} onChange={handleItemsPerPageChange}>
            <option value={10}>10</option>
            <option value={25}>25</option>
            <option value={50}>50</option>
          </select>
        </label>
      </div>
      <ul>
        {currentItems.map((item, index) => (
          <li key={index}>{item.name}</li>
        ))}
      </ul>
      <div>
        {Array.from({ length: Math.ceil(items.length / itemsPerPage) }, (_, index) => (
          <button
            key={index}
            onClick={() => handlePageChange(index + 1)}
            disabled={currentPage === index + 1}
          >
            {index + 1}
          </button>
        ))}
      </div>
    </div>
  );
}

export default Paginator;
