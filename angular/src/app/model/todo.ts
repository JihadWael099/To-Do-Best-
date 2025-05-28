import { ToDoDetails } from "./to-do-details";

export interface Todo {
     id?: number;
  title: string;
  userId?: number;
  details_id?: ToDoDetails;
  completed?: boolean;
}
