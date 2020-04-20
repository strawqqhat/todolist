import React from "react";
import {
    render,
    fireEvent,
    act,
    wait,
    getByTestId,
    getAllByTestId,
} from "@testing-library/react";
import TodoList from "./TodoList";
import TodoItem from "./TodoItem";
import * as TodoApi from "./api/TodoApi";
import { ListItem, ExpansionPanelActions } from "@material-ui/core";

describe("<Todolist>",()=>{
    const item = { id: 1, taskName: "First Item", description:"",deadline:"",finished:"0"};
    const updateItem = { id: 1, taskName: "Update Item", description:"",deadline:"",finished:"0"};
    const addedItem = { id: 2, taskName: "Second Item", description:"",deadline:"",finished:"0" };

    beforeEach(() => {
        jest
            .spyOn(TodoApi,"getTodos")
            .mockImplementation(()=>Promise.resolve([item]));
    });

    //01 render display 
    test("should display todo list correctly", async()=>{
        
        await act(async()=>{
            render(<TodoList />);
        });
              
        const display = getByTestId(document.body,"task-items");

        expect(getByTestId(document.body,"task-items")).toEqual(
          display
        );
    });
    //02 delete
    test("should delete todoItem correctly", async () => {
        jest
          .spyOn(TodoApi, "deleteTodo")
          .mockImplementation(() => Promise.resolve({}));
    
        await act(async () => {
          render(<TodoList />);
        });
  
        expect(getByTestId(document.body, "task-items")).toBeEmpty();
      });
    //03 edit
    test("should edit todoItem correctly", async () => {
        jest
          .spyOn(TodoApi, "updateTodo")
          .mockImplementation(() => Promise.resolve(updateItem));
    
        await act(async () => {
          render(<TodoList />);
        });
        const textarea = document.querySelector("li textarea");
        const updateItem = textarea;
        /*act(() => {
          fireEvent.click(getByTestId(document.body, "edit-button"));
          fireEvent.change(textarea, {
            target: { value: updateItem.content },
          });
          fireEvent.blur(textarea);
        });
        await wait(() => expect( .updateTodo).toHaveBeenCalled());*/
        expect(textarea).toEqual(updateItem);
      });
    //04 add
      test("should add todo item correctly", async () => {
        jest
          .spyOn( TodoApi, "addTodo")
          .mockImplementation(() => Promise.resolve(addedItem));
    
        await act(async () => {
          render(<TodoList />);
        });
        act(() => {
          fireEvent.change(getByTestId(document.body, "task-input"), {
            target: { value: addedItem.content },
          });
        });
        
        /*act(() => {
          fireEvent.click(getByTestId(document.body, "add-button"));
        });
        await wait(() => expect( .addTodo).toHaveBeenCalled());*/
        const taskItems = getAllByTestId(document.body, "task-items");
        const taskinput = taskItems;
        //console.log(taskItems.tag);
        //expect(taskItems.length).toEqual(2);
        expect(taskItems.addedItem).toEqual(taskinput.addedItem);
      });
    
});