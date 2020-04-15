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
//api   import * as TodoApi from "./api/TodoApi";
import { ListItem, ExpansionPanelActions } from "@material-ui/core";

describe("<Todolist>",()=>{
    const ;
    const ;

    beforeEach(() => {
        jest
            .spyOn(,"getTodos")
            .mockImplementation(()=>Promise.resolve([ListItem]));
    });

    //01 render display
    test("should display todo list correctly", async()=>{
        await act(async()=>{
            render(<Todolist />);
        });
        expect(getByTestId(document.body,"task-item")).toHaveTextContent(
            "First Item"
        );
    });
    //02 delete
    test("should delete todoItem correctly", async () => {
        jest
          .spyOn(, "deleteTodo")
          .mockImplementation(() => Promise.resolve({}));
    
        await act(async () => {
          render(<TodoList />);
        });
    
        act(() => {
          fireEvent.click(getByTestId(document.body, "delete-button"));
        });
        await wait(() => expect( .deleteTodo).toHaveBeenCalled());
        expect(getByTestId(document.body, "task-items")).toBeEmpty();
      });
    //03 edit
      test("should edit todoItem correctly", async () => {
        jest
          .spyOn(, "updateTodo")
          .mockImplementation(() => Promise.resolve(updateItem));
    
        await act(async () => {
          render(<TodoList />);
        });
        const textarea = document.querySelector("li textarea");
        act(() => {
          fireEvent.click(getByTestId(document.body, "edit-button"));
          fireEvent.change(textarea, {
            target: { value: updateItem.content },
          });
          fireEvent.blur(textarea);
        });
        await wait(() => expect( .updateTodo).toHaveBeenCalled());
        expect(textarea.value).toEqual(updateItem.content);
      });
    //04 add
      test("should add todo item correctly", async () => {
        jest
          .spyOn( , "addTodo")
          .mockImplementation(() => Promise.resolve(addedItem));
    
        await act(async () => {
          render(<TodoList />);
        });
        act(() => {
          fireEvent.change(getByTestId(document.body, "task-input"), {
            target: { value: addedItem.content },
          });
        });
    
        act(() => {
          fireEvent.click(getByTestId(document.body, "add-button"));
        });
        await wait(() => expect( .addTodo).toHaveBeenCalled());
        const taskItems = getAllByTestId(document.body, "task-item");
        expect(taskItems.length).toEqual(2);
        expect(taskItems[1]).toHaveTextContent(addedItem.content);
      });
    //05 finished display
      test("should finished Item correctly", async()=>{
        jest 
        
        await act(async()=>{
            render();
        });
        act(()=>{

        });
        await

        expect();
        expect();
      })
});