import * as eventService from "../service/EventService";
import { vi } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import EventDetails from "../components/EventDetails";
import { act } from "react-dom/test-utils";

global.fetch = vi.fn() as unknown as jest.Mock;

describe("Test EventService component", () => {
  test("get events successfully", async () => {
    //Given
    const mockedEvents = [
      {
        name: "Concert A",
        dateTime: "2025-02-01 20:00",
        venue: "Stadium A",
        ticketPrice: 50,
      },
    ];
    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockedEvents),
    });

    //When
    const result = await eventService.fetchEvents();

    //Then
    expect(fetch).toHaveBeenCalledWith("http://localhost:8080/events");
    expect(result).toEqual(mockedEvents);
  });

  test("get events throws error when not successful", async () => {
    //Given
    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
      json: () => Promise.resolve([]),
    });

    //When, Then
    await expect(eventService.fetchEvents()).rejects.toThrowError();
  });

  test("create event successfully"),
    async () => {
      //Given
      const eventData = {
        name: "Concert A",
        dateTime: "2025-02-01T20:00",
        venue: "Stadium A",
        ticketPrice: 50,
        maxCapacity: 100,
        organizerDetails: "Organizer A",
      };

      (fetch as jest.Mock).mockResolvedValueOnce({
        ok: true,
        text: () => Promise.resolve("Event created successfully"),
      });

      //When
      const result = await eventService.createEvent(eventData);

      //Then
      expect(fetch).toHaveBeenCalledWith(
        "http://localhost:8080/events",
        expect.objectContaining({
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(eventData),
        })
      );
      expect(result).toBe("Event created successfully");
    };

  test("create event throws error when not successful", async () => {
    //Given
    const eventData = {
      name: "Concert A",
      dateTime: "2025-02-01T20:00",
      venue: "Stadium A",
      ticketPrice: 50,
      maxCapacity: 100,
      organizerDetails: "Organizer A",
    };
    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
      json: () => Promise.resolve([]),
    });

    //When, Then
    await expect(eventService.createEvent(eventData)).rejects.toThrowError();
  });
  test("delete event successfully", async () => {
    // Given
    const eventId = "123";

    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      text: () => Promise.resolve("Event deleted successfully"),
    });

    // When
    const result = await eventService.deleteEvent(eventId);

    // Then
    expect(fetch).toHaveBeenCalledWith(
      `http://localhost:8080/events/${eventId}`,
      expect.objectContaining({
        method: "DELETE",
      })
    );
    expect(result).toBe("Event deleted successfully");
  });
  test("delete event throws error when not successful", async () => {
    // Given
    const eventId = "123";

    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
      text: () => Promise.resolve("Error deleting event"),
    });

    // When, Then
    await expect(eventService.deleteEvent(eventId)).rejects.toThrowError();
  });

  test("edit event successfully", async () => {
    // Given
    const eventId = "123";
    const eventData = {
      name: "Updated Concert A",
      dateTime: "2025-02-01T21:00",
      venue: "Updated Stadium A",
      ticketPrice: 60,
      maxCapacity: 120,
      organizerDetails: "Updated Organizer A",
    };

    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      text: () => Promise.resolve("Event updated successfully"),
    });

    // When
    const result = await eventService.editEvent(eventId, eventData);

    // Then
    expect(fetch).toHaveBeenCalledWith(
      `http://localhost:8080/events/${eventId}`,
      expect.objectContaining({
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(eventData),
      })
    );
    expect(result).toBe("Event updated successfully");
  });

  test("edit event throws error when not successful", async () => {
    // Given
    const eventId = "123";
    const eventData = {
      name: "Updated Concert A",
      dateTime: "2025-02-01T21:00",
      venue: "Updated Stadium A",
      ticketPrice: 60,
      maxCapacity: 120,
      organizerDetails: "Updated Organizer A",
    };

    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
      text: () => Promise.resolve("Error updating event"),
    });

    // When, Then
    await expect(
      eventService.editEvent(eventId, eventData)
    ).rejects.toThrowError();
  });

  test("delete event successfully", async () => {
    const mockEvent = {
      id: "1",
      name: "Updated Concert A",
      dateTime: "2025-02-01T21:00",
      venue: "Updated Stadium A",
      ticketPrice: 60,
      maxCapacity: 120,
      organizerDetails: "Updated Organizer A",
    };
    const mockSetResponse = vi.fn();
    const mockFetchEvents = vi.fn();

    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      text: () => Promise.resolve("Event deleted successfully"),
    });

    render(
      <EventDetails
        event={mockEvent}
        setResponse={mockSetResponse}
        setOpenPopup={() => {}}
        fetchEvents={mockFetchEvents}
      />
    );

    const deleteButton = screen.getByText("Delete");
    await act(async () => {
      fireEvent.click(deleteButton);
    });
    expect(fetch).toHaveBeenCalledWith(
      "http://localhost:8080/events/1",
      expect.objectContaining({
        method: "DELETE",
      })
    );
    await waitFor(() =>
      expect(mockSetResponse).toHaveBeenCalledWith(
        "Event deleted successfully",
        "success"
      )
    );
    expect(mockFetchEvents).toHaveBeenCalled();
  });
});
