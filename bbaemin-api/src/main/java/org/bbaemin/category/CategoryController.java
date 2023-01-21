package org.bbaemin.category;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @GetMapping
    public Object listCategory() {
        return "{\n" +
                "\"code\": 200, \n" +
                "\"data\": [\n" +
                "{\n " +
                "\"idx\": 1, \n" +
                "\"categoryName\": \"상품\", \n" +
                "\"categoryDesc\": \"상품\", \n" +
                "\"parentId\": null, \n" +
                "\"createdBy\": \"master\", \n" +
                "\"createdAt\": \"2023-01-01, \"" +
                "\"modifiedBy\": null, \n" +
                "\"modifiedAt\": null \n" +
                "}\n" +
                "],\n" +
                "\"length\": 1 \n" +
                "}";
    }

    @GetMapping("/{idx}")
    public Object getCategory(@PathVariable Long idx) {
        return "{\n" +
                "\"code\": 200, \n" +
                "\"data\": [\n" +
                "{\n " +
                "\"idx\": 1, \n" +
                "\"categoryName\": \"상품\", \n" +
                "\"categoryDesc\": \"상품\", \n" +
                "\"parentId\": null, \n" +
                "\"createdBy\": \"master\", \n" +
                "\"createdAt\": \"2023-01-01, \"" +
                "\"modifiedBy\": null, \n" +
                "\"modifiedAt\": null \n" +
                "}\n" +
                "],\n" +
                "\"length\": 1 \n" +
                "}";
    }

    @PostMapping
    public Object createCategory(CategoryDto categoryDTO) {
        return "{\n" +
                "\"code\": 200, \n" +
                "\"data\": [\n" +
                "{\n " +
                "\"idx\": 1, \n" +
                "\"categoryName\": \"상품\", \n" +
                "\"categoryDesc\": \"상품\", \n" +
                "\"parentId\": null, \n" +
                "\"createdBy\": \"master\", \n" +
                "\"createdAt\": \"2023-01-01, \"" +
                "\"modifiedBy\": null, \n" +
                "\"modifiedAt\": null \n" +
                "}\n" +
                "],\n" +
                "\"length\": 1 \n" +
                "}";
    }

    @PutMapping("/{idx}")
    public Object updateCategory(@PathVariable Long idx) {
        return "{\n" +
                "\"code\": 200, \n" +
                "\"data\": [\n" +
                "{\n " +
                "\"idx\": 1, \n" +
                "\"categoryName\": \"상품\", \n" +
                "\"categoryDesc\": \"상품\", \n" +
                "\"parentId\": null, \n" +
                "\"createdBy\": \"master\", \n" +
                "\"createdAt\": \"2023-01-01, \"" +
                "\"modifiedBy\": null, \n" +
                "\"modifiedAt\": null \n" +
                "}\n" +
                "],\n" +
                "\"length\": 1 \n" +
                "}";
    }

    @DeleteMapping("/{idx}")
    public Object deleteCategory(@PathVariable Long idx) {
        return "{\n" +
                "\"code\": 200, \n" +
                "\"data\": [\n" +
                "{\n " +
                "\"idx\": 1, \n" +
                "\"categoryName\": \"상품\", \n" +
                "\"categoryDesc\": \"상품\", \n" +
                "\"parentId\": null, \n" +
                "\"createdBy\": \"master\", \n" +
                "\"createdAt\": \"2023-01-01, \"" +
                "\"modifiedBy\": null, \n" +
                "\"modifiedAt\": null \n" +
                "}\n" +
                "],\n" +
                "\"length\": 1 \n" +
                "}";
    }
}
