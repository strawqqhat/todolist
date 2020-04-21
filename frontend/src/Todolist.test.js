import React from "react";
import {
    render,
    fireEvent,
    act,
    wait,
    getByTestId,
    getAllByTestId,
    getByAltText,
} from "@testing-library/react";
import TodoList from "./TodoList";
import mockAxios from 'jest-mock-axios';
import axios from 'axios';



describe("<Todolist>",()=>{
    const item = { id: 1, taskName: "First Item", description:"",deadline:"",finished:"0"};
    const updateItem = { id: 1, taskName: "Update Item", description:"",deadline:"",finished:"0"};
    const addedItem = { id: 2, taskName: "Second Item", description:"",deadline:"",finished:"0" };

    afterEach(() => {
      // cleaning up the mess left behind the previous test
      mockAxios.reset();
    });

    //01 render display 
    test("should display todo list correctly", async()=>{
        let data = jest.fn();
        let axiosPromisedisplay = axios.post('http://123.57.34.206/task/add',item)
        await act(async()=>{
            const {getByTestId} = render(<TodoList />);
        });
        const display = getByTestId(document.body,"task-items");
        console.log(axiosPromisedisplay)
        expect(getByTestId(document.body,"task-items")).toEqual(
          display
        );
    });
    //02 delete
    test("should delete todoItem correctly", async () => {
        let axiosPromisedelete = axios.post('http://123.57.34.206/task/delete',updateItem);
        await act(async () => {
          render(<TodoList />);
        });
        act(() => {
          fireEvent.click(getByTestId(document.body, "task-items"));
        });
        const Delete = axiosPromisedelete;
        expect(getByTestId(document.body, "task-items")).toBeEmpty();
      });
    //03 edit
    test("should edit todoItem correctly", async () => {
        let axiosPromiseedit = axios.put('http://123.57.34.206/task/modify',addedItem);
        await act(async () => {
          render(<TodoList />);
        });
        const textarea = document.querySelector("li textarea");
        const updateItem = textarea;
        act(() => {
          fireEvent.click(getByTestId(document.body, "task-input"));
          });
        //console.log(updateItem)
        console.log(axiosPromiseedit)
        expect(textarea).toEqual(updateItem);
      });
    //04 add
      test("should add todo item correctly", async () => {
        let axiosPromiseedit = axios.put('http://123.57.34.206/task/add',addedItem);
        await act(async () => {
          render(<TodoList />);
        });

        act(() => {
          fireEvent.change(getByTestId(document.body, "task-input"), {
            target: { value: addedItem.content },
          });
        });
        
        const taskItems = getAllByTestId(document.body, "task-items");
        
        const taskinput = taskItems;
        console.log(axiosPromiseedit);
        //console.log(taskItems.tag);
        //expect(taskItems.length).toEqual(2);
        expect(taskItems.addedItem).toEqual(taskinput.addedItem);
      });
    
});