import { EventData } from "../domain/models/EventData";
import { TicketData } from "../domain/models/TicketData";
import { User } from "../domain/models/User";

const baseUrl = "http://localhost:8080/events";
export const fetchEvents = async () => {
  const response = await fetch(baseUrl);
  if (!response.ok) {
    throw new Error();
  }
  const eventsData = await response.json();
  return eventsData;
};

export const createEvent = async (eventData: EventData) => {
  const response = await fetch(baseUrl, {
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
  const url = baseUrl + "/" + id;
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
  const url = baseUrl + "/" + id;
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

export const buyTicket = async (ticketData: TicketData) => {
  const url = baseUrl + "/" + ticketData.eventId + "/register";
  const response = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(ticketData),
  });
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage);
  }
  return await response.text();
};
export const fetchAttendeesByEvent = async (
  eventId: string
): Promise<User[]> => {
  const url = `${baseUrl}/${eventId}/attendees`;
  const response = await fetch(url);
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage);
  }
  const attendeesData: User[] = await response.json();
  const uniqueAttendees = Object.values(
    attendeesData.reduce((acc: Record<string, User>, attendee) => {
      acc[attendee.email] = attendee;
      return acc;
    }, {})
  );
  return uniqueAttendees;
};
