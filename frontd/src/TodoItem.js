import React, {useState} from "react";
import TextField from "@material-ui/core/TextField";
import "./style.css"
//使用useState Hook
const TodoItem= ({todo,deletehandler})=>{
const [todoThing,settodo]=useState(todo);
const [edit,setedit]=useState(false)
    const onclick=event=>{
    setedit(!edit)
    }
    const onchange=event=>{
    settodo({...todo,taskName:event.target.value})
    }
    return (
            <li key={todoThing.id}>
                <TextField className="text"
                           value={todoThing.taskName}
                           disabled={!edit}
                            onChange={onchange}

                />
                <button className="edit" type={"submit"} onClick={onclick}>修改</button>
                <button className="delete" type={"submit"} onClick={() => deletehandler(todoThing.id, todoThing.taskName)}>删除</button>

            </li>
        )


}

export default TodoItem