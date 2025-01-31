import * as eventService from "../service/EventService";
import { vi } from "vitest";

global.fetch = vi.fn();

describe("Test EventService component", async () => {
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
    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockedEvents),
    });

    //When
    const result = await eventService.fetchEvents();

    //Then
    expect(fetch).toHaveBeenCalledWith("http://localhost:8080/getAll");
    expect(result).toEqual(mockedEvents);
  });

  test("get events throws error", async () => {
    //Given
    fetch.mockResolvedValueOnce({
      ok: false,
      json: () => Promise.resolve([]),
    });

    //When, Then
    await expect(eventService.fetchEvents()).rejects.toThrowError();
  });
});
