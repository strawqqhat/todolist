import React ,{Component}from "react";
import axios from "axios";
import qs from "qs";
class TodoList extends Component {
    constructor(props) {
        super(props);
        this.state={
           inputThing:'',
            error:'',
            hasError: false,
            todos: []

        }
    }
    //从后端获取数据
    componentDidMount() {
        axios.get('/api/list').then(res=>{
            const todos=res.data;
             console.log(JSON.stringify(todos["data"]))
            this.setState({
                todos:todos["data"]
            })

    })
    }
    onchangeHandler=event=>{
        this.setState({
            inputthing:event.target.value
        })
    }
//添加事项
    addhandler=event=>{
        const {inputthing,todos}=this.state;
        const newthing={
            taskName:inputthing,
            description:"",
            finished:0
        };
        axios.post('/api/add',qs.stringify(newthing)).then(res=>{
            console.log(res);
            console.log(res.data);
            //更新所有事项
            const box=[...todos];
            box.push(newthing);
            this.setState({
                inputthing:'',
                todos:box,
                error:"",
                hasError:false
            });
        }).catch(err=>{console.log(err)},
            )
    }
    render() {
        const {inputthing,todos,error,hasError}=this.state;
        return(
            <div>
                <input
                type="text"
                value={inputthing}
                data-testid={task-input}
                onChange={this.onchangeHandler}
                />
                <button onClick={this.addhandler} data-test-id={add-button}>提交</button>
                {
                    todos.map(todo=><ul key={todo.id}>{todo.taskName}</ul>)
                }

            </div>
        )

    }
}export default TodoList;