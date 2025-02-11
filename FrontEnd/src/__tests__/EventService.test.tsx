import * as eventService from "../service/EventService";
import { vi } from "vitest";

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
});
