import { TicketType } from "./BuyTicketState";

export interface TicketData {
  eventId?: string;
  firstName: string;
  lastName: string;
  email: string;
  ticketType: TicketType;
}
