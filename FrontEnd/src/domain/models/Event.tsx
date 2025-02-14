interface Event {
  id: string;
  name: string;
  dateTime: string;
  venue: string;
  ticketPrice: number;
  maxCapacity: number;
  organizerDetails: string;
}

export default Event;
