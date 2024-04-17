//import logo from './logo.svg';
//import './App.css';
import { Routes, Route, BrowserRouter } from "react-router-dom";
import ShowEmployees from './Components/ShowEmployees';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<ShowEmployees></ShowEmployees>}></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App;
