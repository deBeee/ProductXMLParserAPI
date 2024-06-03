# ProductXMLParserAPI

## Overview

This API provides endpoints for handling product data from an XML file. The endpoints allow users to get the count of products, retrieve all products, and find a specific product by name.

## Endpoints

| HTTP Method | Endpoint          | Description                               | Parameters                                                                                                           |
|-------------|-------------------|-------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| POST        | `/products/count` | Returns the count of products in the file | - `file`: MultipartFile - The XML file containing product data                                                       |
| POST        | `/products/all`   | Returns all products from the file        | - `file`: MultipartFile - The XML file containing product data                                                       |
| POST        | `/products/find`  | Returns a product by its name             | - `file`: MultipartFile - The XML file containing product data<br>- `name`: String - The name of the product to find |

## Endpoint Details

### POST `/products/count`

**Description**: This endpoint returns the number of products in the provided XML file.

**Parameters**:
- `file` (MultipartFile): The XML file containing product data.

**Response**:
- 200 OK: Returns a JSON object with the count of products.

Example:
```json
{
    "numberOfProducts": 3
}
```

### POST `/products/all`
**Description**: This endpoint returns all products from the provided XML file.

**Parameters**:
- `file` (MultipartFile): The XML file containing product data.

**Response**:
- 200 OK:  Returns a JSON object with a list of all products.

Example:
```json
{
  "products": [
    {
      "id": 1,
      "name": "apple",
      "category": "fruit",
      "partNumberNR": "2303-E1A-G-M-W209B-VM",
      "companyName": "FruitsAll",
      "active": true
    },
    {
      "id": 2,
      "name": "orange",
      "category": "fruit",
      "partNumberNR": "5603-J1A-G-M-W982F-PO",
      "companyName": "FruitsAll",
      "active": false
    },
    {
      "id": 3,
      "name": "glass",
      "category": "dish",
      "partNumberNR": "9999-E7R-Q-M-K287B-YH",
      "companyName": "HomeHome",
      "active": true
    }
  ]
}
```

### POST `/products/find`
**Description**: This endpoint returns a specific product by its name from the provided XML file.

**Parameters**:
- `file` (MultipartFile): The XML file containing product data.
- `name` (String): The name of the product to find

**Response**:
- 200 OK: Returns a JSON object with the product details if found.
- 404 NOT FOUND: Returns a JSON object with an error message if the product is not found.

Example: 
```json
{
  "id": 1,
  "name": "apple",
  "category": "fruit",
  "partNumberNR": "2303-E1A-G-M-W209B-VM",
  "companyName": "FruitsAll",
  "active": true
}
```
Error:
```json
{
"status": 404,
"message": "Product with name {name} not found",
"name": "{name}"
}
```


## Error Handling

The API includes global exception handling for the following scenarios:

- **ProductNotFoundException**: Thrown when a product with the specified name is not found. (described above)
- **XmlStreamReadException**: Thrown when XML file does not contain xml content.
- **XmlDatabindException**: Thrown when XML file contains xml content with incorrect format or structure.
- **XmlIOException**: Thrown when there is an I/O error when processing the XML file.

## Error Responses

### XmlStreamReadException
**HTTP Status Code:** 400 Bad Request
**Response Body:**
```json
{
  "status": 400,
  "message": "Failed to read XML stream, please ensure the file is well-formed and try again",
  "cause": "{cause}"
}
```

### XmlDatabindException
**HTTP Status Code:** 400 Bad Request
**Response Body:**
```json
{
  "status": 400,
  "message": "Failed to parse XML data, please check the file format correctness and ensure it matches the expected structure",
  "cause": "{cause}"
}
```

### XmlIOException
**HTTP Status Code:** 500 Internal Server Error
**Response Body:**
```json
{
  "status": 500,
  "message": "I/O error occurred while processing the XML file, please check the file and try again",
  "cause": "{cause}"
}
```

## Product Validator

The `ProductValidator` class is responsible for validating the fields of a `Product` object.
It ensures that the `name`, `category`, `partNumberNR`, and `companyName` fields match specific regex patterns.

### Validation Rules

- **Name**: Must contain only letters and spaces.
- **Category**: Must contain only letters and spaces.
- **PartNumberNR**: Must follow the pattern `^\d{4}-[A-Z]\d([A-Z]-){3}[A-Z]\d{3}[A-Z]-[A-Z]{2}$`.
- **CompanyName**: Must contain only letters and spaces.

