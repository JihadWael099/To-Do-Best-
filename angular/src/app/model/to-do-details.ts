import { Priority } from "./priority";
import { Status } from "./status";
import { ToDoEntity } from "./to-do-entity";
export interface ToDoDetails {
  id: number;
  description?: string;
  priority: Priority;
  createAt?: string;      
  startAt: string;        
  finishAt: string;
  status: Status;
  entityId?: number;
  userId?: number;
  todoEntity?:ToDoEntity;
}
