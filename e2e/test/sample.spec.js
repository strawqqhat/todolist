describe('Todo List', function () {
    let page;
    let newTaskContent;

    before (async function () {
        page = await browser.newPage();
        let random = new Date().getMilliseconds();
        newTaskContent = 'new task item' + random;
        await page.goto('http://127.0.0.1:3000');
    });

    after (async function () {
        await page.close();
    });
    
    describe('get all tasks', function() {

        it('should return all tasks correctly', async function () {

            // 通过li标签获取任务项的数目
            const li_num = await page.$$eval('#root > div > div > li', list=>list.length);
            // 通过.delete标签获取任务项的数目
            const delete_btn_num = await page.$$eval('.delete', list=>list.length);
            // 若通过两种方法获得的任务项数目相同，认为可以正确获取任务列表
            expect (li_num).to.eql(delete_btn_num);
            
        });
    });

    describe('add a new task', function() {
        it('should add a new task correctly', async function(){

            await page.click('.task-input', {delay: 500});
            await page.type('.task-input', newTaskContent, {delay: 500});
            await page.click('.submit');
            // 之前没有设置等待时间总是导致新录入信息没加载出来就去获取，得不到新数据
            await page.waitFor(1000);
            // 每个li标签包含一个任务项，:last-child选取最后一个任务项，也就是新增的任务项
            let newTask = await page.waitFor('#root > div > div > li:last-child');
            // 使用querySelector找到input标签，新增任务项的内容在input标签中的value属性里
            const expectNewTask = await page.evaluate(task=>task.querySelector('input').value, newTask);
            expect (expectNewTask).to.eql(newTaskContent);

        });
    });

    describe('delete a task', function() {
        it('should delete a task correctly', async function() {
            let todoList = await page.$$('#root > div > div > li');
            const len1 = todoList.length;
            await page.evaluate(()=> {
                document.querySelector('.delete').click()
            });
            await page.waitFor(1000);
            const len2 = await page.$$eval('#root > div > div > li', list=>list.length);
            expect (len1-len2).to.eql(1);
        });
    });

    describe('edit a task', function() {
        it('should edit a task correctly', async function() {

            const newContent = 'updated task';
            // 点击最后一个任务的编辑按钮
            await page.click('#root > div > div > li:last-child .edit', {delay:500});
            // 点击最后一个任务的输入框三下使该任务原有内容被选中
            let updatedTask = await page.waitFor('#root > div > div > li:last-child');
            const editArea = await page.$('#root > div > div > li:last-child > div');
            await editArea.click({clickCount: 3});
            // 在被选中的原有内容上进行编辑，修改为新的内容
            await editArea.type(newContent, {delay: 500});
            // 获取编辑完成后该任务项的新的内容
            await page.click('#root > div > div > li:last-child .edit', {delay:500});
            const expectUpdatedTask = await page.evaluate(task=>task.querySelector('input').value, updatedTask);
            // 若获取到的新的内容与输入内容一致则认为编辑功能测试是正确的
            expect (expectUpdatedTask).to.eql(newContent);

        });
    });
    
});