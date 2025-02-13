import { EventData } from "../models/EventData";

export const fetchEvents = async () => {
  const response = await fetch("http://localhost:8080/events");
  if (!response.ok) {
    throw new Error();
  }
  const eventsData = await response.json();
  return eventsData;
};

export const createEvent = async (eventData: EventData) => {
  const response = await fetch("http://localhost:8080/events", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(eventData),
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage);
  }
  return await response.text();
};

export const deleteEvent = async (id: string) => {
  const url = "http://localhost:8080/events/" + id;
  const response = await fetch(url, {
    method: "DELETE",
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage);
  }
  return await response.text();
};

export const editEvent = async (id: string, eventData: EventData) => {
  const url = "http://localhost:8080/events/" + id;
  const response = await fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(eventData),
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage);
  }
  return await response.text();
};
