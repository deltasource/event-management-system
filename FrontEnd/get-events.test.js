const { getAllEvents } = require('./get-events');

describe('getAllEvents', () => {
  let eventsContainer;

  beforeEach(() => {
    document.body.innerHTML = `
      <ul class="events-list"></ul>
    `;
    global.fetch = jest.fn();
    eventsContainer = document.querySelector('.events-list');
  });

  it('should fetch and display events', async () => {
    //Given
    fetch.mockResolvedValueOnce({
      json: jest.fn().mockResolvedValue({
        events: [
          {
            name: 'Event 1',
            date: '2025-01-01',
            venue: 'Venue 1',
            category: 'Category 1',
            ticketPrice: '$50',
          },
          {
            name: 'Event 2',
            date: '2025-02-01',
            venue: 'Venue 2',
            category: 'Category 2',
            ticketPrice: '$100',
          },
        ],
      }),
    });

    //When
    await getAllEvents();

    //Then
    expect(fetch).toHaveBeenCalledTimes(1);
    expect(eventsContainer.childElementCount).toBe(2); // We have two events
    expect(eventsContainer.children[0].textContent).toContain('Event 1');
    expect(eventsContainer.children[1].textContent).toContain('Event 2');
  });

  it('should handle errors', async () => {
    //Given
    fetch.mockRejectedValueOnce(new Error());
    console.error = jest.fn();

    //When
    await getAllEvents();

    //Then
    expect(console.error).toHaveBeenCalledWith('Error loading the events data:', expect.any(Error));
  });
});
