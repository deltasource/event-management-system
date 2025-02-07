export default interface CreateEventState {
  name: string;
  date: string;
  time: string;
  venue: string;
  ticketPrice: number;
  maxCapacity: number;
  organizerDetails: string;
  errors: {
    name?: string;
    venue?: string;
    date?: string;
    time?: string;
    ticketPrice?: string;
    maxCapacity?: string;
    organizerDetails?: string;
  };
}
