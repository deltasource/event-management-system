import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav className="p-3 navbar navbar-dark bg-dark">
      <ul className="navbar-nav flex flex-row gap-3">
      <li className="nav-item">
          <Link className="nav-link text-light" to="/">Home</Link>
        </li>
        <li className="nav-item">
          <Link className="nav-link text-light" to="/events">Events</Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navbar;