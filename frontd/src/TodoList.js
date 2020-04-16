import React, {Component} from "react";
import axios from "axios";
import qs from "qs";
import TodoItem from "./TodoItem";
import "./style.css";

class TodoList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            inputThing: '',
            error: '',
            hasError: false,
            todos: []

        }
    }

    //从后端获取数据
    componentDidMount() {
        axios.get('/api/list').then(res => {
            const todos = res.data;
            console.log(JSON.stringify(todos["data"]))
            this.setState({
                todos: todos["data"]
            })

        })
        console.log(this.state.todos)
    }

    onchangeHandler = event => {
        this.setState({
            inputthing: event.target.value
        })
    }
//添加事项
    addhandler = event => {
        const {inputthing, todos} = this.state;
        if (inputthing === undefined || inputthing==='')
            console.log("字符为空");
        else {const newthing = {
            taskName: inputthing,
            description: "",
            finished: 0
        };
        console.log(inputthing)
            axios.post('/api/add', qs.stringify(newthing)).then(res => {
                console.log(res.data);
                //更新所有事项
                const box = res.data["data"];
                this.setState({
                    inputthing: '',
                    todos: box,
                    error: "",
                    hasError: false
                });
            }).catch(err => {
                    console.log(err)
                },
            )}

    }
    //删除事项
    deletehandler = (id, taskName) => {
        const deletething = {id: id, taskName: taskName};
        console.log(deletething);
        axios.delete('/api/delete', {params:deletething}).then(res => {
            console.log(res.data);
            const todos = res.data;
            this.setState(
                {
                    inputThing: '',
                    error: '',
                    hasError: false,
                    todos: todos["data"]
                }
            )
            console.log(this.state.todos)
        }).catch(err => console.log(err))

    }

    render() {
        const {inputthing, todos, error, hasError} = this.state;
        return (
            <div>
                <input
                    className="task-input"
                    type="text"
                    value={inputthing}
                    onChange={this.onchangeHandler}
                />
                <button className="submit" onClick={this.addhandler}>提交</button>
                {
                    todos.map(todo => {
                        return (<TodoItem
                            key={todo.id}
                            todo={todo}
                            deletehandler={this.deletehandler}
                        />)

                    })
                }

            </div>
        )

    }
}

export default TodoList;