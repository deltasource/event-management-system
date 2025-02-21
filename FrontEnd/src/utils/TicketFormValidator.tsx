import BuyTicketState from "../domain/models/BuyTicketState";

export function TicketFormValidator(state: BuyTicketState) {
  let isInputValid = true;
  let errors: BuyTicketState["errors"] = {};
  const { firstName, lastName, email, ticketType } = state;
  if (!firstName) {
    errors.firstName = "First name is required.";
    isInputValid = false;
  } else if (firstName.length < 2) {
    errors.firstName = "First name must be minimum 2 characters long.";
    isInputValid = false;
  }
  if (!lastName) {
    errors.lastName = "Last name is required.";
    isInputValid = false;
  } else if (lastName.length < 2) {
    errors.lastName = "Last name must be minimum 2 characters long.";
    isInputValid = false;
  }
  if (!email) {
    errors.email = "Email is required.";
    isInputValid = false;
  } else if (!validateEmail(email)) {
    errors.email = "Please enter a valid email address.";
    isInputValid = false;
  }
  if (!ticketType) {
    errors.ticketType = "Ticket type is required.";
    isInputValid = false;
  }

  return { isInputValid, errors };
}

function validateEmail(email: string): boolean {
  const regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
  return regex.test(email);
}
