import * as Yup from "yup";
import BuyTicketState from "../domain/models/BuyTicketState";

const validationSchema = Yup.object({
  firstName: Yup.string()
    .required("First name is required.")
    .min(2, "First name must be minimum 2 characters long."),
  lastName: Yup.string()
    .required("Last name is required.")
    .min(2, "Last name must be minimum 2 characters long."),
  email: Yup.string()
    .required("Email is required.")
    .email("Please enter a valid email address."),
  ticketType: Yup.string().required("Ticket type is required."),
});

export function TicketFormValidator(state: BuyTicketState) {
  try {
    validationSchema.validateSync(state, { abortEarly: false });
    return { isInputValid: true, errors: {} };
  } catch (err: any) {
    const errors = err.inner.reduce((acc: any, error: any) => {
      acc[error.path] = error.message;
      return acc;
    }, {});
    return { isInputValid: false, errors };
  }
}
