import HomePage from "./HomePage";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";

test("rendering the home page", () => {
  //When
  render(<HomePage />);

  //Then
  const heading = screen.getByRole("h1");
  expect(heading).toHaveTextContent("");
});
