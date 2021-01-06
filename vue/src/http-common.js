import axios from 'axios';

export default axios.create({
  baseURL: 'http://localhost:8000/_api',
  headers: {
    'Content-type': 'application/json',
  },
});
