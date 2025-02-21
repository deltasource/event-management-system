export default interface BuyTicketState {
  firstName: string;
  lastName: string;
  email: string;
  ticketType: TicketType;
  errors: {
    firstName?: string;
    lastName?: string;
    email?: string;
    ticketType?: string;
  };
}

export enum TicketType {
  GENERAL = "GENERAL",
  VIP = "VIP",
  STUDENT = "STUDENT",
}
