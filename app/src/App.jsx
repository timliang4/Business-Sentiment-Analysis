import { useState } from 'react'
import { useEffect } from 'react'
import './App.css'
import axios from 'axios'

export default function App() {
  const [businessList, setBusinessList] = useState([])
  const [isLoading, setIsLoading] = useState(false) 
  const [placeID, setPlaceID] = useState('');
  useEffect(() => {
    let ignore = false
    const fetchBusinesses = async () => {
      try {
        const res = await axios.get("http://localhost:8080/business/")
        if (!ignore) {
          console.log("get request")
          setBusinessList(res.data._embedded ? res.data._embedded.businesses : [])
        }
      } catch(e) {
        console.log("ERROR: ", e)
      }
    }

    fetchBusinesses();

    return () => {
      ignore = true
    }
  }, [isLoading])

  return (
    <div>
      <h1>Business Sentiment Analysis</h1>
      <hr />
      <h2>Analyze a New Business</h2>
      <p>Add a business below using this <a target="_blank" 
        href="https://developers.google.com/maps/documentation/javascript/examples/places-placeid-finder">Place ID Finder</a></p> 
      <form onSubmit={(e) => {
        e.preventDefault();
        setIsLoading(true);
        fetch(`http://localhost:8080/business/${placeID}`, {method: 'POST'})
          .then((res) => {
            (res.status === 404) && alert("Invalid Place ID or API key"); 
            setIsLoading(false);
          })
          .catch(e => {
            alert("Error posting data! Check console for more details.")
            setIsLoading(false);
          })
      }}>
        <label htmlFor="placeID">Place ID: </label>
        <input type="text" id='placeID' onChange={(e) => setPlaceID(e.target.value)}/>
        <button type="submit" disabled={isLoading}>Search</button>
      </form>
      <hr /> 
      <h2>Businesses</h2>
      {isLoading && <h3>Loading...</h3>}
      {businessList.map(business => {
        return <Business
          key={business._links.self.href}
          link={business._links.self.href}
          _name={business.name}
          _avgSent={business.averageSentiment}
          _sentVar={business.sentimentVariance}
          isLoading={isLoading}
          setIsLoading={setIsLoading}>
        </Business>
      })}
      <hr />
    </div>
  )
}

function Business({link, _name, _avgSent, _sentVar, isLoading, setIsLoading}) {
  console.log("re-rendering " + _name)

  const [isViewing, setIsViewing] = useState(true)
  const [name, setName] = useState(_name)
  const [avgSent, setAvgSent] = useState(_avgSent)
  const [sentVar, setSentVar] = useState(_sentVar)

  useEffect(() => {
    setName(_name)
    setAvgSent(_avgSent)
    setSentVar(_sentVar)
  }, [_name, _avgSent, _sentVar])

  return (
    <div>
      <form onSubmit={(e) => {
          e.preventDefault();
          if (!isViewing) {
            setIsLoading(true);
            fetch(`${link}?` + new URLSearchParams({
              name: name, averageSentiment: avgSent, sentimentVariance: sentVar
            }), {method: 'PUT'})
              .then(() => {
                setIsLoading(false)
              })
              .catch((e) => {
                alert("Error putting data! Check console for more details.")
                console.log("ERROR", e)
                setIsLoading(false)
              })
          }
          setIsViewing(!isViewing);
        }}>
        <h3>{(isViewing) ? <a href={`https://www.google.com/maps/place/?q=place_id:${link.split('/').pop()}`}>{name}</a> : <input 
          value={name}
          onChange={(e => {setName(e.target.value)})}></input>}</h3>
        <p><b>Average Sentiment: </b>{(isViewing) ? avgSent : <input 
          value={avgSent} onChange={(e) => {setAvgSent(e.target.value)}}></input>}</p> 
        <p><b>Sentiment Variance: </b>{(isViewing) ? sentVar : <input
          value={sentVar} onChange={(e) => {setSentVar(e.target.value)}}></input>}</p>
        <button onClick={() => {
          setIsLoading(true);
          fetch(link, {method: 'PATCH'})
            .then(() => {
              setIsLoading(false);
            })
            .catch(e => {
              alert("Error patching data! Check console for more details.")
              console.log("ERROR", e)
              setIsLoading(false);
            })
        }} disabled={isLoading || !isViewing} type="button">Update</button>
        <button type="submit" disabled={isLoading}>{(isViewing) ? "Edit" : "Save"}</button>
        <button onClick={() => {
          setIsLoading(true);
          fetch(link, {method: 'DELETE'})
            .then(() => {
              setIsLoading(false);
            })
            .catch((e) => {
              alert("Error deleting data! Check console for more details.") 
              console.log("ERROR", e)
              setIsLoading(false);
            })
        }} disabled={isLoading || !isViewing} type="button">Delete</button>
      </form>
    </div>
    )  
}
