import CreateEventState from "../domain/models/EventFormState";
export function EventValidator(state: CreateEventState) {
  const {
    name,
    date,
    time,
    venue,
    ticketPrice,
    maxCapacity,
    organizerDetails,
  } = state;
  let isValid = true;
  let errors: CreateEventState["errors"] = {};

  if (!name) {
    errors.name = "Event name is required.";
    isValid = false;
  } else if (name.length < 2) {
    errors.name = "Event name must be longer than 2 characters.";
    isValid = false;
  }

  if (!date) {
    errors.date = "Event date cannot be null.";
    isValid = false;
  }

  if (!time) {
    errors.time = "Event time cannot be null.";
    isValid = false;
  }

  if (maxCapacity <= 0) {
    errors.maxCapacity = "Maximum capacity must be a positive number.";
    isValid = false;
  }

  if (!venue) {
    errors.venue = "Venue cannot be null.";
    isValid = false;
  } else if (venue.length < 2 || venue.length > 40) {
    errors.venue = "Venue name must be between 2 and 40 characters.";
    isValid = false;
  }

  if (!organizerDetails) {
    errors.organizerDetails = "Organizer details cannot be null.";
    isValid = false;
  } else if (organizerDetails.length < 2 || organizerDetails.length > 40) {
    errors.organizerDetails =
      "Organizer details must be between 2 and 40 characters.";
    isValid = false;
  }

  if (ticketPrice <= 0) {
    errors.ticketPrice = "Ticket price must be a positive value.";
    isValid = false;
  }

  return { isValid, errors };
}
