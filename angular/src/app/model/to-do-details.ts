import { Priority } from "./priority";
import { Status } from "./status";
export interface ToDoDetails {
   id?: number;
  description?: string;
  priority?: Priority;
  createAt?: Date;
  startAt?: Date;
  finishAt?: Date;
  status?: Status;
  entityId?: number;
  userId: number;
}
