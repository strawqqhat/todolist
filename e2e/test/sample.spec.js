describe('Todo List', function () {
    let page;
    before (async function () {
        page = await browser.newPage();
        await page.goto('http://127.0.0.1:3000');
    });

    after (async function () {
        await page.close();
    });
    describe('get all tasks', function() {
        it('should return all tasks correctly', async function () {
            let todoList = await page.$$('#root > div > div > li', input=>input.value);
            console.log(todoList.length);
        });
    });
    
});