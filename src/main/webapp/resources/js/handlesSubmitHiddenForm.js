let hiddenForms = $$(".hidden-form");
// let submitButtons = $$(".submit-button");
let deleteButtons = $$(".delete-button");
let editButtons = $$(".edit-button");
let handlesSubmitHiddenForm = {
    start: () => {
        for (let i = 0; i < deleteButtons.length; i++) {
            deleteButtons[i].addEventListener("click", ()=> {
                hiddenForms[i].action = 'delete-film';
                hiddenForms[i].submit();
            })
        }

        for (let i = 0; i < editButtons.length; i++) {
            editButtons[i].addEventListener("click", () => {
                alert("Test hehe");
            })

        }
    }
}

handlesSubmitHiddenForm.start();


