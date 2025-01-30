export const fetchEvents = async () => {
  const response = await fetch("http://localhost:8080/getAll");
  if (!response.ok) {
    throw new Error();
  }
  const eventsData = await response.json();
  return eventsData;
};
