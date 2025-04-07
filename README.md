# ComposeExamples
The aim of this project is to include multiple relistatc examples with a single app

<img width="511" alt="image" src="https://github.com/user-attachments/assets/c9c491ee-be0c-4de2-afa0-a97b1241e96f" />

We will first land on the `Home` page with showing you a random cat fact with 2 types charater count. There are also 2 buttons for reloading the cat fact and to save the cat fact to favourties (room db)

<img width="511" alt="image" src="https://github.com/user-attachments/assets/ebbe3937-6578-4ca5-8a27-b48d39d3a537" />

We can go to the `Favourites` page by clicking the `Favourites` tab on the bottom nav bar, there are a title to display the number of saved cat facts, a button to delete all of the saved facts. Saved cat facts can be reviewed on the page, each tile contains the info of it and a delete button for removing it from the db.

<img width="338" alt="image" src="https://github.com/user-attachments/assets/ea64ea26-9d78-41e1-bdb5-e3b98dfe7415" />
<img width="511" alt="image" src="https://github.com/user-attachments/assets/a75d45b3-15a4-4d59-a9b4-fbaa6e9e811c" />
<img width="511" alt="image" src="https://github.com/user-attachments/assets/e9352950-b70d-42b5-a3e8-2ab399144e1b" />

The last page is called `List` page, it is a page combined on demand data fetching and nested scrolls. Every time we reach the bottom of the page, there is a text "Loading more..." which fetch more cat facts (has a check in the VM to prevent dupliactation) behind the scenes. The subtitle of the page displays  the number of loaded cat facts, this is the first item to disappear and reappear when we do scrolling.
