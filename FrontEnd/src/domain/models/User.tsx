import { TicketData } from "./TicketData";

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  tickets: TicketData[];
}
