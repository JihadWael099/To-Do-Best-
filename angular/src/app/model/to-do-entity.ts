import { ToDoDetails } from "./to-do-details";

export interface ToDoEntity {
  id: number;
  title: string;
  userId: number;
  details_id?: ToDoDetails; 
}
