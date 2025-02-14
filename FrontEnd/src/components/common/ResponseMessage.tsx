import React, { useState, useEffect } from "react";
import { Snackbar, Alert } from "@mui/material";
import ResponseMessageProps from "../../domain/types/ResponseMessageProps";

/**
 * Component, responsible for presenting an error message within a Snackbar component
 **/
export default function ResponseMessage(responseProps: ResponseMessageProps) {
  const message = responseProps.message;
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (message) {
      setOpen(true);
    }
  }, [message]);

  const handleClose = () => {
    setOpen(false);
    responseProps.setResponseMessage("");
  };

  useEffect(() => {}, [open]);

  return (
    <React.Fragment>
      <Snackbar
        open={open}
        autoHideDuration={5000}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center",
        }}
      >
        <Alert
          severity={responseProps.severity}
          sx={{ width: "100%" }}
          onClose={handleClose}
        >
          {message}
        </Alert>
      </Snackbar>
    </React.Fragment>
  );
}
