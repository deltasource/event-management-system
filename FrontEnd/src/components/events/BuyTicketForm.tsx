import { useState } from "react";
import { EventComponentProps } from "../../domain/types/EventComponentProps";
import { TicketFormValidator } from "../../utils/TicketFormValidator";
import { TicketData } from "../../domain/models/TicketData";
import { buyTicket } from "../../service/EventService";
import {
  Grid,
  Button,
  TextField,
  Select,
  MenuItem,
  SelectChangeEvent,
} from "@mui/material";
import BuyTicketState, { TicketType } from "../../domain/models/BuyTicketState";
import PopupElement from "../common/Popup";
import ConfirmationElement from "../common/ConfirmationElement";

export default function BuyTicket({
  event,
  setResponse,
  setOpenPopup,
  fetchEvents,
}: EventComponentProps) {
  const [buyTicketState, setBuyTicketState] = useState<BuyTicketState>({
    firstName: "",
    lastName: "",
    email: "",
    ticketType: TicketType.GENERAL,
    errors: {},
  });
  const [confirmBuyingTicketPopup, setConfirmBuyingTicketPopup] =
    useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const ticketData: TicketData = {
      eventId: event?.id,
      firstName: buyTicketState.firstName,
      lastName: buyTicketState.lastName,
      email: buyTicketState.email,
      ticketType: buyTicketState.ticketType,
    };

    try {
      let response;
      response = await buyTicket(ticketData);
      setResponse(response, "success");
      setOpenPopup(false);
      fetchEvents();
    } catch (error: any) {
      setResponse(error.message, "error");
    }
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setBuyTicketState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSelectChange = (e: SelectChangeEvent<string>) => {
    const { name, value } = e.target;
    setBuyTicketState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleCancelation = () => {
    setConfirmBuyingTicketPopup(false);
  };

  const handleConfirmation = () => {
    if (handleValidation()) {
      setConfirmBuyingTicketPopup(true);
    }
  };

  const handleValidation = (): boolean => {
    setBuyTicketState((prevState) => ({
      ...prevState,
      errors: {},
    }));
    const { isInputValid, errors } = TicketFormValidator(buyTicketState);
    if (!isInputValid) {
      setBuyTicketState((prevState) => ({
        ...prevState,
        errors,
      }));
      return false;
    }
    return true;
  };

  return (
    <div>
      <h2>Event: {event?.name}</h2>
      <form>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              id="firstName"
              label="First Name"
              name="firstName"
              value={buyTicketState.firstName}
              onChange={onChange}
              error={!!buyTicketState.errors.firstName}
              helperText={buyTicketState.errors.firstName || ""}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              id="lastName"
              label="Last Name"
              name="lastName"
              value={buyTicketState.lastName}
              onChange={onChange}
              error={!!buyTicketState.errors.lastName}
              helperText={buyTicketState.errors.lastName || ""}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              id="email"
              label="Email"
              name="email"
              value={buyTicketState.email}
              onChange={onChange}
              error={!!buyTicketState.errors.email}
              helperText={buyTicketState.errors.email || ""}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <Select
              id="ticketType"
              label="Ticket Type"
              name="ticketType"
              value={buyTicketState.ticketType}
              defaultValue="DEFAULT"
              onChange={handleSelectChange}
              displayEmpty
              fullWidth
            >
              <MenuItem value="DEFAULT" selected disabled hidden>
                Select Ticket Type
              </MenuItem>
              <MenuItem value={TicketType.GENERAL}>GENERAL</MenuItem>
              <MenuItem value={TicketType.VIP}>VIP</MenuItem>
              <MenuItem value={TicketType.STUDENT}>STUDENT</MenuItem>
            </Select>
          </Grid>
          <Grid item xs={12}>
            <Button
              className="bg-warning text-dark"
              type="button"
              onClick={handleConfirmation}
            >
              Buy ticket
            </Button>
            <PopupElement
              title="CONFIRM EVENT DELETION"
              openPopup={confirmBuyingTicketPopup}
              setOpen={setConfirmBuyingTicketPopup}
            >
              <ConfirmationElement
                title={`Are you sure you want to purchase a ticket for ${event?.name}?`}
                confirmColor="success"
                cancelColor="error"
                icon="🎫"
                onCancel={handleCancelation}
                onConfirm={handleSubmit}
              />
            </PopupElement>
          </Grid>
        </Grid>
      </form>
    </div>
  );
}
