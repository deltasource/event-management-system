import { Component } from "react";
import * as eventService from "../service/EventService.tsx";
import CreateEventState from "../models/CreateEventState.tsx";
import { EventData } from "../models/EventData.tsx";
import { Grid, Button, TextField } from "@mui/material";
import { CreateEventProps } from "../models/CreateEventProps.tsx";
import { EventValidator } from "../utils/EventValidator.tsx";

export default class CreateEvent extends Component<
  CreateEventProps,
  CreateEventState
> {
  constructor(props: CreateEventProps) {
    super(props);
    this.state = {
      name: "",
      date: "",
      time: "",
      venue: "",
      ticketPrice: 0,
      maxCapacity: 0,
      organizerDetails: "",
      errors: {},
    };
  }

  onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    this.setState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  submitForm = async (e: React.FormEvent) => {
    e.preventDefault();
    const { isValid, errors } = EventValidator(this.state);
    if (!isValid) {
      this.setState({ errors });
      return;
    }
    const {
      name,
      venue,
      ticketPrice,
      maxCapacity,
      date,
      time,
      organizerDetails,
    } = this.state;
    const dateTime = date && time ? `${date}T${time}` : "";
    const eventData: EventData = {
      name,
      dateTime,
      venue,
      ticketPrice,
      maxCapacity,
      organizerDetails,
    };
    try {
      const response = await eventService.createEvent(eventData);
      this.props.setCreateEventResponse(response, "success");
      this.props.setOpenPopup(false);
      this.props.fetchEvents();
    } catch (error: any) {
      this.props.setCreateEventResponse(error.message, "error");
    }
  };
  render() {
    const {
      name,
      date,
      time,
      venue,
      ticketPrice,
      maxCapacity,
      organizerDetails,
    } = this.state;
    return (
      <div>
        <p>Fill the input fields to create new event:</p>
        <form onSubmit={this.submitForm}>
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.name}
                id="name"
                label="Event Name"
                value={name}
                name="name"
                onChange={this.onChange}
                helperText={this.state.errors.name || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.venue}
                id="venue"
                label="Venue"
                value={venue}
                name="venue"
                onChange={this.onChange}
                helperText={this.state.errors.venue || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.date}
                id="date"
                label="Event Date"
                type="date"
                value={date}
                name="date"
                onChange={this.onChange}
                InputLabelProps={{ shrink: true }}
                helperText={this.state.errors.date || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.time}
                id="time"
                label="Event Time"
                type="time"
                value={time}
                name="time"
                onChange={this.onChange}
                InputLabelProps={{ shrink: true }}
                helperText={this.state.errors.time || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.ticketPrice}
                id="ticketPrice"
                label="Ticket Price"
                type="number"
                value={ticketPrice}
                name="ticketPrice"
                onChange={this.onChange}
                helperText={this.state.errors.ticketPrice || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.maxCapacity}
                id="maxCapacity"
                label="Max Capacity"
                type="number"
                value={maxCapacity}
                name="maxCapacity"
                onChange={this.onChange}
                helperText={this.state.errors.maxCapacity || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                error={!!this.state.errors.organizerDetails}
                id="organizerDetails"
                label="Organizer Details"
                value={organizerDetails}
                name="organizerDetails"
                onChange={this.onChange}
                helperText={this.state.errors.organizerDetails || ""}
                fullWidth
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <Button
                className="btn-secondary bg-secondary"
                type="submit"
                style={{ color: "white" }}
              >
                Create Event
              </Button>
            </Grid>

            <Grid
              item
              xs={12}
              md={6}
              sx={{ display: "flex", justifyContent: "flex-end" }}
            ></Grid>
          </Grid>
        </form>
      </div>
    );
  }
}
